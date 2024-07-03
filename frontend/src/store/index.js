import { configureStore } from '@reduxjs/toolkit';
import paymentReducer from './paymentSlice';
import webhookReducer from './webhookSlice';

const store = configureStore({
  reducer: {
    payment: paymentReducer,
    webhook: webhookReducer,
  },
});

export default store;
