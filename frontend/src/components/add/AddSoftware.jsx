import React, { useState, useEffect } from "react";
import "./index.scss";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import {
  addSoftware,
  getSoftwareCompanies,
  getSoftwares,
  renewSoftware,
} from "../../service/SoftwareService";

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
    id: "",
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

  const [software, setSoftware] = useState([]);

  const [manualCompanyName, setManualCompanyName] = useState(""); // Manually entered company name

  useEffect(() => {
    getSoftwareCompanies()
      .then((data) => {
        setCompanies(data);
      })
      .catch((error) => console.error("Error fetching companies:", error));

    getSoftwares()
      .then((data) => {
        setSoftware(data);
      })
      .catch((error) => console.error("Error fetching software:", error));
  }, []);

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
    } else {
      const manuallyEnteredName = manualCompanyName.trim(); // Trim whitespace
      console.log("Manually Entered Name:", manuallyEnteredName);
      setFormData({
        ...formData,
        company: {
          id: "",
          name: manuallyEnteredName,
        },
      });
      setSelectedCompanyId("");
    }
  };

  const handleRenewSubmit = async () => {
    try {
      await renewSoftware(renewData.id, {
        cost: renewData.cost,
        expiryDate: renewData.expiryDate,
        company: renewData.company,
      });
      toast.success("Software renewed successfully!", { autoClose: 3000 });
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
        console.log(formData);
        toast.success("Software added successfully!", { autoClose: 3000 });
      } catch (error) {
        console.error("Error adding software:", error);
        toast.error("Failed to add the software. Please try again.", {
          autoClose: 3000,
        });
      }
    } else if (mode === "Renew") {
      try {
        await handleRenewSubmit();
      } catch (error) {
        console.error("Error renewing software:", error);
        toast.error("Failed to renew the software. Please try again.", {
          autoClose: 3000,
        });
      }
    }
  };

  return (
    <div className="form-container">
      <h2>{mode === "Add" ? "Add Software" : "Renew Software"}</h2>
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
                value={manualCompanyName}
                onChange={(e) => {
                  const enteredValue = e.target.value;
                  setManualCompanyName(enteredValue);
                  console.log("Manually Entered Company Name:", enteredValue);
                }}
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
        </div>
      )}
      {mode === "Renew" && (
        <div>
          <form className="form" onSubmit={handleSubmit}>
            <div>
              <label>Software ID to Renew:</label>
              <select
                name="renewData.id"
                value={renewData.id}
                onChange={(e) =>
                  setRenewData({ ...renewData, id: e.target.value })
                }
                required
              >
                <option value="">Select a Software</option>
                {software.map((sw) => (
                  <option key={sw.id} value={sw.id}>
                    {sw.id} - {sw.name}
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
                  <option key={company.id} value={company.name}>
                    {company.name}
                  </option>
                ))}
              </select>
            </div>
            <div>
              <label>Company ID:</label>
              <input
                type="number"
                name="renewData.company.id"
                value={renewData.company.id}
                onChange={handleChange}
              />
            </div>
            <button className="submit-button" type="submit">
              Renew Software
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

export default SoftwareForm;
