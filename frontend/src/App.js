import "./App.css";

import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./pages/home/Home";
import Login from "./pages/login/Login";
import List from "./pages/Softwarelist/SoftwareList";
import AddSoftwarePage from "./pages/addsoftware/AddSoftwarePage";
import ExpiredSoftwareList from "./pages/expiredsoftware/ExpiredSoftwareList";
import AboutToExpireSoftwareList from "./pages/abouttoexpiresoftware/AboutToExpireSoftwareList";
import SoftwareComapniesList from "./pages/softwarecompanies/SoftwareCompanies";
import AllDevicesList from "./pages/devicepages/devicelist/DeviceList";
import AboutToExpireDeviceList from "./pages/devicepages/abouttoexpiredevice/AboutToExpireDeviceList";
import ExpiredDeviceList from "./pages/devicepages/expireddevice/ExpiredDeviceList";
import DeviceCompaniesList from "./pages/devicepages/devicecompanies/DeviceCompanies";

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/">
            <Route index element={<Home />} />
            <Route path="login" element={<Login />} />
            <Route path="software/">
              <Route index element={<List />} />
              <Route path="expiredSoftware" element={<ExpiredSoftwareList/>}/>
              <Route path="abouttoexpireSoftware" element={<AboutToExpireSoftwareList />} />
              <Route path="softwarecompanies" element={<SoftwareComapniesList />} />
            </Route>
            <Route path="device/">
              <Route index element={<AllDevicesList/>} />
              <Route path="expireddevice" element={<ExpiredDeviceList />}/>
              <Route path="abouttoexpireDevice" element={<AboutToExpireDeviceList />} />
              <Route path="devicecompanies" element={< DeviceCompaniesList/>} />
            </Route>
          </Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
