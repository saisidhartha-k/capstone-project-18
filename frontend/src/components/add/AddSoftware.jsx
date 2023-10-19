import React, { useState, useEffect } from 'react';
import './index.scss'

function SoftwareForm() {
  const [formData, setFormData] = useState({
    name: '',
    company: {
      id: '',
      name: '',
    },
    numberOfEmployees: 0,
    cost: 0,
    expiryDate: '',
    isExpired: false,
  });

  const [companies, setCompanies] = useState([]);
  const [selectedCompanyId, setSelectedCompanyId] = useState('');

  useEffect(() => {
    fetch('http://localhost:8080/softwarecompany/getcompanies')
      .then((response) => response.json())
      .then((data) => {
        setCompanies(data);
      })
      .catch((error) => console.error('Error fetching companies:', error));
  }, []);

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

  const handleCompanySelect = (e) => {
    const selectedCompany = companies.find((company) => company.name === e.target.value);

    if (selectedCompany) {
      setFormData({
        ...formData,
        company: {
          id: selectedCompany.id,
          name: selectedCompany.name,
        },
      });
      setSelectedCompanyId(selectedCompany.id);
    }
  };

  const addSoftware = async (softwareData) => {
    try {
      const response = await fetch('http://localhost:8080/software/addsoftware', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(softwareData),
      });

      if (response.ok) {
        const data = await response.json();
        return data;
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

    } catch (error) {
      console.error('Error adding software:', error);
    }
  };

  return (
    <div className="form-container">
      <h2>Software Form</h2>
      <form className="form" onSubmit={handleSubmit}>
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
          <label>Company Name (Select):</label>
          <select
            name="company.name"
            value={formData.company.name}
            onChange={handleCompanySelect}
          >
            <option value="">Select a Company</option>
            {companies.map((company) => (
              <option key={company.id} value={company.name}>
                {company.name}
              </option>
            ))}
          </select>
        </div>
        <div>
          <label>Company Name (Manual):</label>
          <input
            type="text"
            name="company.name"
            value={formData.company.name}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Company ID:</label>
          <input
            type="number"
            name="company.id"
            value={selectedCompanyId}
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
        <button  className="submit-button" type="submit">Submit</button>
      </form>
    </div>
  );
}

export default SoftwareForm;
