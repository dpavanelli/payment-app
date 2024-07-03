import { createSlice } from '@reduxjs/toolkit';

const webhookSlice = createSlice({
  name: 'webhook',
  initialState: {
    url: '',
  },
  reducers: {
    setUrl: (state, action) => {
      state.url = action.payload;
    },
    resetForm: (state) => {
      state.url = '';
    },
  },
});

export const { setUrl, resetForm } = webhookSlice.actions;

export default webhookSlice.reducer;
    