import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Form, Input, Button, Card, message } from 'antd';
import { ExclamationCircleOutlined } from '@ant-design/icons';
import { setUrl, resetForm } from '../store/webhookSlice';
import { submitForm } from '../services/api';
import { styles } from '../styles';

const Webhooks = () => {
  const dispatch = useDispatch();
  const { url } = useSelector((state) => state.webhook);
  const [loading, setLoading] = useState(false);
  const [form] = Form.useForm();

  const onFinish = async () => {
    setLoading(true);
    const formData = { url };

    try {
      const response = await submitForm('/webhooks', formData);

      if (response.ok) {
        message.success('Webhook registered successfully');
        dispatch(resetForm());
        form.resetFields();
      } else if (response.status === 400) {
        const errorData = await response.json();
        const fields = errorData.map(err => ({
          name: err.field,
          errors: [<span><ExclamationCircleOutlined /> {err.error}</span>],
        }));
        form.setFields(fields);
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
      <Card title="Webhook registration" bordered={true} style={styles.card}>
        <Form
          form={form}
          name="webhook"
          layout="vertical"
          initialValues={{ remember: true }}
          onFinish={onFinish}
          disabled={loading}
        >
          <Form.Item
            label="URL"
            name="url"
            rules={[{ required: true, message: 'Please enter the URL!' }]}
          >
            <Input value={url} onChange={(e) => dispatch(setUrl(e.target.value))} />
          </Form.Item>

          <Form.Item>
            <Button type="primary" htmlType="submit" loading={loading}>
              Submit
            </Button>
          </Form.Item>
        </Form>
      </Card>
    </div>
  );
};

export default Webhooks;
