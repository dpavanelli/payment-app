import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { Input, Button, Card, message, Row, Col } from 'antd';
import { ExclamationCircleOutlined } from '@ant-design/icons';
import { resetForm } from '../store/paymentSlice';
import { submitForm } from '../services/api';
import { styles } from '../styles';

const Payments = () => {
  const dispatch = useDispatch();
  const [loading, setLoading] = useState(false);
  const [firstName, setFirstNameState] = useState('');
  const [lastName, setLastNameState] = useState('');
  const [zipCode, setZipCodeState] = useState('');
  const [cardNumber, setCardNumber] = useState('');
  const [errors, setErrors] = useState({});

  const handleCardNumberChange = (e) => {
    const value = e.target.value;
    // Remove all non-numeric characters and format
    const cleanedValue = value.replace(/\D/g, '').slice(0, 16);
    const formattedValue = cleanedValue.replace(/(\d{4})(?=\d)/g, '$1 ').trim();
    setCardNumber(formattedValue);
  };

  const validateFields = () => {
    const newErrors = {};
    if (!firstName) newErrors.firstName = 'Please enter your first name!';
    if (!lastName) newErrors.lastName = 'Please enter your last name!';
    if (!zipCode) newErrors.zipCode = 'Please enter your zip code!';
    if (!cardNumber || cardNumber.replace(/\s/g, '').length !== 16) newErrors.cardNumber = 'Please enter a valid card number!';
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async () => {
    if (!validateFields()) return;

    setLoading(true);
    // Clean the card number by removing spaces
    const cleanedCardNumber = cardNumber.replace(/\s/g, '');
    const formData = { firstName, lastName, zipCode, cardNumber: cleanedCardNumber };

    try {
      const response = await submitForm('/payments', formData);

      if (response.ok) {
        message.success('Payment saved successfully');
        dispatch(resetForm());
        setFirstNameState('');
        setLastNameState('');
        setZipCodeState('');
        setCardNumber('');
        setErrors({});
      } else if (response.status === 400) {
        const errorData = await response.json();
        const newErrors = {};
        errorData.forEach(err => {
          newErrors[err.field] = err.error;
        });
        setErrors(newErrors);
      } else if (response.status === 500) {
        message.error('Server error. Please try again later.');
      }
    } catch (error) {
      message.error('Network error. Please try again later.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={styles.container}>
      <Card title="New Payment" bordered={true} style={styles.card}>
        <Row gutter={16}>
          <Col span={24}>
            <label>First Name</label>
            <Input
              value={firstName}
              onChange={(e) => setFirstNameState(e.target.value)}
              placeholder="First Name"
            />
            {errors.firstName && <span style={{ color: 'red' }}><ExclamationCircleOutlined /> {errors.firstName}</span>}
          </Col>
        </Row>
        <Row gutter={16} style={{ marginTop: 16 }}>
          <Col span={24}>
            <label>Last Name</label>
            <Input
              value={lastName}
              onChange={(e) => setLastNameState(e.target.value)}
              placeholder="Last Name"
            />
            {errors.lastName && <span style={{ color: 'red' }}><ExclamationCircleOutlined /> {errors.lastName}</span>}
          </Col>
        </Row>
        <Row gutter={16} style={{ marginTop: 16 }}>
          <Col span={24}>
            <label>Zip Code</label>
            <Input
              value={zipCode}
              onChange={(e) => setZipCodeState(e.target.value)}
              placeholder="Zip Code"
            />
            {errors.zipCode && <span style={{ color: 'red' }}><ExclamationCircleOutlined /> {errors.zipCode}</span>}
          </Col>
        </Row>
        <Row gutter={16} style={{ marginTop: 16 }}>
          <Col span={24}>
            <label>Card Number</label>
            <Input
              value={cardNumber}
              onChange={handleCardNumberChange}
              placeholder="Card Number"
            />
            {errors.cardNumber && <span style={{ color: 'red' }}><ExclamationCircleOutlined /> {errors.cardNumber}</span>}
          </Col>
        </Row>
        <Row style={{ marginTop: 16 }}>
          <Col span={24}>
            <Button type="primary" onClick={handleSubmit} loading={loading}>
              Submit
            </Button>
          </Col>
        </Row>
      </Card>
    </div>
  );
};

export default Payments;
