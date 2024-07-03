export const submitForm = async (endpoint, formData) => {
  // Make a copy of formData to ensure we do not mutate the original object
  const cleanedData = { ...formData };

  // Clean the card number by removing spaces and underscores and ensuring it has 16 characters
  if (cleanedData.cardNumber) {
    cleanedData.cardNumber = cleanedData.cardNumber.replace(/\s|_/g, '').slice(0, 16);
  }
  
  return await fetch(process.env.REACT_APP_API_URL + endpoint, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(cleanedData),
  });
};
