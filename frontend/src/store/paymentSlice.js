import { createSlice } from '@reduxjs/toolkit';

const paymentSlice = createSlice({
  name: 'payment',
  initialState: {
    firstName: '',
    lastName: '',
    zipCode: '',
    cardNumber: '',
  },
  reducers: {
    setFirstName: (state, action) => {
      state.firstName = action.payload;
    },
    setLastName: (state, action) => {
      state.lastName = action.payload;
    },
    setZipCode: (state, action) => {
      state.zipCode = action.payload;
    },
    setCardNumber: (state, action) => {
      state.cardNumber = action.payload;
    },
    resetForm: (state) => {
      state.firstName = '';
      state.lastName = '';
      state.zipCode = '';
      state.cardNumber = '';
    },
  },
});

export const { setFirstName, setLastName, setZipCode, setCardNumber, resetForm } = paymentSlice.actions;

export default paymentSlice.reducer;
