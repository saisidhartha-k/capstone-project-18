import React, { useState, useEffect } from "react";
import "./index.scss";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { renewSoftware } from "../../service/SoftwareService";

const SoftwareRenewalForm = () => {
  const [id, setId] = useState("");
  const [softwareData, setSoftwareData] = useState({
    cost: 0,
    expiryDate: "",
    company: {
      id: "",
      name: "",
    },
  });

  const [companies, setCompanies] = useState([]);
  const [selectedCompanyId, setSelectedCompanyId] = useState("");

  useEffect(() => {
    fetch("http://localhost:8080/softwarecompany/getcompanies")
      .then((response) => response.json())
      .then((data) => {
        setCompanies(data);
      })
      .catch((error) => console.error("Error fetching companies:", error));
  }, []);
  const showCustomNotification = () => {
    toast.info("Custom notification message", {
      autoClose: 3000
    });
  };

  

  const handleRenewal = async () => {
    try {
      const renewedSoftware = await renewSoftware(id, softwareData);
      console.log("Renewed software:", renewedSoftware);
    } catch (error) {
      console.error("Error renewing software:", error.message);
    }
  };

  return (
    <div>
      <h2>Software Renewal Form</h2>
      <input
        type="text"
        placeholder="Software ID"
        value={id}
        onChange={(e) => setId(e.target.value)}
      />
      <input
        type="number"
        placeholder="Cost"
        value={softwareData.cost}
        onChange={(e) =>
          setSoftwareData({ ...softwareData, cost: e.target.value })
        }
      />
      <input
        type="date"
        placeholder="Expiry Date"
        value={softwareData.expiryDate}
        onChange={(e) =>
          setSoftwareData({ ...softwareData, expiryDate: e.target.value })
        }
      />
      <input
        type="number"
        placeholder="Company"
        value={softwareData.company.id}
        onChange={(e) =>
          setSoftwareData({ ...softwareData, company: e.target.value })
        }
      />
      <button onClick={handleRenewal}>Renew Software</button>
    </div>
  );
};

export default SoftwareRenewalForm;
