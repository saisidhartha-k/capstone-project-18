import React, { useState, useEffect } from "react";
import {
  addDevice,
  getAllDeviceCompanies,
  renewDevice,
  getDevices,
} from "../../service/DeviceService";
import "./index.scss";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function DeviceForm() {
  const [formData, setFormData] = useState({
    name: "",
    company: {
      id: "",
      name: "",
    },
    numberOfEmployees: 0,
    cost: 0,
    expiryDate: "",
    location: "",
  });

  const [renewData, setRenewData] = useState({
    id: "",
    cost: 0,
    expiryDate: "",
    company: {
      id: "",
      name: "",
    },
  });

  const [companies, setCompanies] = useState([]);
  const [devices, setDevices] = useState([]);
  const [selectedCompanyId, setSelectedCompanyId] = useState("");
  const [mode, setMode] = useState("Add");
  const [newCompany, setNewCompany] = useState({
    name: "",
    description: "",
  });

  useEffect(() => {
    getAllDeviceCompanies()
      .then((data) => {
        setCompanies(data);
      })
      .catch((error) =>
        console.error("Error fetching device companies:", error)
      );

    getDevices()
      .then((data) => {
        setDevices(data);
      })
      .catch((error) => console.error("Error fetching devices:", error));
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
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

  const handleCompanyManualInput = (field, e) => {
    const value = e.target.value;
    setFormData((prevFormData) => ({
      ...prevFormData,
      company: {
        ...prevFormData.company,
        [field]: value,
      },
    }));
  };

  const handleRenewSubmit = async (e) => {
    e.preventDefault();

    try {
      await renewDevice(renewData.id, {
        cost: renewData.cost,
        expiryDate: renewData.expiryDate,
        company: renewData.company,
      });

      toast.success("Device renewed successfully!", { autoClose: 3000 });
    } catch (error) {
      console.error("Error renewing device:", error);
      toast.error("Failed to renew the device. Please try again.", {
        autoClose: 3000,
      });
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await addDevice(formData);
      console.log("Device added:", response);
      toast.success("Device added successfully!", { autoClose: 3000 });
    } catch (error) {
      console.error("Error adding device:", error);
      toast.error("Failed to add the device. Please try again.", {
        autoClose: 3000,
      });
    }
  };

  return (
    <div className="form-container">
      <h2>Device Form</h2>
      {mode === "Add" && (
        <div>
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
              <label>Or Enter a New Company Name:</label>
              <input
                type="text"
                name="company.name"
                value={formData.company.name}
                onChange={(e) => handleCompanyManualInput("name",e)}
              />
            </div>
            <div>
              <label>Company Description (Manual):</label>
              <textarea
                name="company.description"
                value={formData.company.description}
                onChange={(e) => handleCompanyManualInput("description",e)}
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
            <div>
              <label>Location:</label>
              <input
                type="text"
                name="location"
                value={formData.location}
                onChange={handleChange}
              />
            </div>
            <button className="submit-button" type="submit">
              Submit
            </button>
          </form>
        </div>
      )}
      {mode === "Renew" && (
        <div>
          <form className="form" onSubmit={handleRenewSubmit}>
            <div>
              <label>Device ID to Renew:</label>
              <select
                name="renewData.id"
                value={renewData.id}
                onChange={(e) =>
                  setRenewData({ ...renewData, id: e.target.value })
                }
                required
              >
                <option value="">Select a Device ID</option>
                {devices.map((device) => (
                  <option key={device.id} value={device.id}>
                    {device.id} - {device.name}
                  </option>
                ))}
              </select>
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
              <label>Company Name (Select):</label>
              <select
                name="renewData.company.name"
                value={renewData.company.name}
                onChange={(e) =>
                  setRenewData({
                    ...renewData,
                    company: { ...renewData.company, name: e.target.value },
                  })
                }
              >
                <option value="">Select a Company</option>
                {companies.map((company) => (
                  <option key={company.id} value={company.id}>
                    {company.id} - {company.name}
                  </option>
                ))}
              </select>
            </div>
            {/* <div>
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
            </div> */}
            <button className="submit-button" type="submit">
              Renew Device
            </button>
          </form>
        </div>
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
        theme="light"
      />
    </div>
  );
}

export default DeviceForm;
