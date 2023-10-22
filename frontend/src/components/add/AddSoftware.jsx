import React, { useState, useEffect } from "react";
import "./index.scss";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import axios from "axios"; // Import the Axios library

const BASE_URL = "http://localhost:8080"; // Update with your API base URL

function SoftwareForm() {
  const [formData, setFormData] = useState({
    name: "",
    company: {
      id: "",
      name: "",
    },
    numberOfEmployees: 0,
    cost: 0,
    expiryDate: "",
    isExpired: false,
  });

  const [renewData, setRenewData] = useState({
    id: "", // Software ID to renew
    cost: 0,
    expiryDate: "",
    company: {
      id: "",
      name: "",
    },
  });

  const [companies, setCompanies] = useState([]);
  const [selectedCompanyId, setSelectedCompanyId] = useState("");
  const [mode, setMode] = useState("Add"); // "Add" or "Renew"

  useEffect(() => {
    fetch(`${BASE_URL}/softwarecompany/getcompanies`)
      .then((response) => response.json())
      .then((data) => {
        setCompanies(data);
      })
      .catch((error) => console.error("Error fetching companies:", error));
  }, []);

  const showCustomNotification = () => {
    toast.info("Custom notification message", {
      autoClose: 3000,
    });
  };

  const handleChange = (e) => {
    const { name, value } = e.target;

    if (name.includes("company.")) {
      const companyField = name.split(".")[1];
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
    const selectedCompany = companies.find(
      (company) => company.name === e.target.value
    );

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
      const response = await fetch(
        "http://localhost:8080/software/addsoftware",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(softwareData),

     
        }
      );
      if (response.ok) {
        toast.success("Software added successfully!", {
          autoClose: 3000, 
        });
      }
    } catch (error) {
      console.error('Error adding device:', error);
      toast.error('Failed to add the software. Please try again.', { autoClose: 3000 });

    }
  };

  const renewSoftware = async () => {
    try {
      const response = await axios.post(
        `${BASE_URL}/software/renew/${renewData.id}`,
        {
          cost: renewData.cost,
          expiryDate: renewData.expiryDate,
          company: renewData.company,
        }
      );

      if (response.status === 200) {
        toast.success("Software renewed successfully!", {
          autoClose: 3000,
        });
      } else {
        toast.error("Failed to renew the software. Please try again.", {
          autoClose: 3000,
        });
      }
    } catch (error) {
      console.error("Error renewing software:", error);
      toast.error("Failed to renew the software. Please try again.", {
        autoClose: 3000,
      });
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (mode === "Add") {
      try {
        await addSoftware(formData);
      } catch (error) {
        console.error("Error adding software:", error);
      }
    } else if (mode === "Renew") {
      try {
        await renewSoftware();
      } catch (error) {
        console.error("Error renewing software:", error);
      }
    }
  };

  return (
    <div className="form-container">
      <h2>{mode === "Add" ? "Add Software" : "Renew Software"}</h2>
      {mode === "Add" && (
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
          <button className="submit-button" type="submit">
            Add Software
          </button>
        </form>
      )}
      {mode === "Renew" && (
        <form className="form" onSubmit={handleSubmit}>
          <div>
            <label>Software ID to Renew:</label>
            <input
              type="text"
              name="renewData.id"
              value={renewData.id}
              onChange={(e) => setRenewData({ ...renewData, id: e.target.value })}
              required
            />
          </div>
          <div>
            <label>Cost:</label>
            <input
              type="number"
              name="renewData.cost"
              value={renewData.cost}
              onChange={(e) =>
                setRenewData({ ...renewData, cost: e.target.value })
              }
            />
          </div>
          <div>
            <label>Expiry Date:</label>
            <input
              type="date"
              name="renewData.expiryDate"
              value={renewData.expiryDate}
              onChange={(e) =>
                setRenewData({ ...renewData, expiryDate: e.target.value })
              }
            />
          </div>
          <div>
            <label>Company ID:</label>
            <input
              type="number"
              name="renewData.company.id"
              value={renewData.company.id}
              onChange={(e) =>
                setRenewData({
                  ...renewData,
                  company: { ...renewData.company, id: e.target.value },
                })
              }
            />
          </div>
          <div>
            <label>Company Name:</label>
            <input
              type="text"
              name="renewData.company.name"
              value={renewData.company.name}
              onChange={(e) =>
                setRenewData({
                  ...renewData,
                  company: { ...renewData.company, name: e.target.value },
                })
              }
            />
          </div>
          <button className="submit-button" type="submit">
            Renew Software
          </button>
        </form>
      )}
      <button onClick={() => setMode(mode === "Add" ? "Renew" : "Add")}>
        {mode === "Add" ? "Switch to Renew Mode" : "Switch to Add Mode"}
      </button>
      <ToastContainer
        position="top-right"
        autoClose={5000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
        theme="dark"
      />
      <ToastContainer />
    </div>
  );
}

export default SoftwareForm;
