import React, { useState } from 'react';

function SoftwareForm() {
  const [formData, setFormData] = useState({
    name: '',
    company: {
      id: 0,
      name: '',
    },
    numberOfEmployees: 0,
    cost: 0,
    expiryDate: '',
    isExpired: false,
  });

  const handleChange = (e) => {
    const { name, value } = e.target;

    if (name.includes('company.')) {
      const companyField = name.split('.')[1];
      setFormData({
        ...formData,
        company: {
          ...formData.company,
          [companyField]: value,
        },
      });
    } else {
      setFormData({
        ...formData,
        [name]: value,
      });
    }
  };

  const addSoftware = async (softwareData) => {
    // Assuming addSoftware is a function to make a POST request
    try {
      const response = await fetch('http://localhost:8080/software/addsoftware', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(softwareData),
      });
      console.log(JSON.stringify(softwareData));

      if (response.ok) {

        const data = await response.json();
        return data;
      } else {
        throw new Error('Failed to add software.');
      }
    } catch (error) {
      throw error;
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await addSoftware(formData);

      console.log('Software added:', response);

      // Optionally, reset the form or redirect the user
    } catch (error) {
      console.error('Error adding software:', error);
      // Handle the error as needed
    }
  };

  return (
    <div>
      <h2>Software Form</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Name:</label>
          <input
            type="text"
            name="name"
            value={formData.name}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label>Company ID:</label>
          <input
            type="number"
            name="company.id"
            value={formData.company.id}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Company Name:</label>
          <input
            type="text"
            name="company.name"
            value={formData.company.name}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Number of Employees:</label>
          <input
            type="number"
            name="numberOfEmployees"
            value={formData.numberOfEmployees}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Cost:</label>
          <input
            type="number"
            name="cost"
            value={formData.cost}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Expiry Date:</label>
          <input
            type="date"
            name="expiryDate"
            value={formData.expiryDate}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Is Expired:</label>
          <input
            type="checkbox"
            name="isExpired"
            checked={formData.isExpired}
            onChange={(e) => setFormData({ ...formData, isExpired: e.target.checked })}
          />
        </div>
        <button type="submit">Submit</button>
      </form>
    </div>
  );
}

export default SoftwareForm;
