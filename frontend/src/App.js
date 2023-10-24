import "./App.css";

import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./pages/home/Home";
import Login from "./pages/login/Login";
import ExpiredSoftwareList from "./pages/expiredsoftware/ExpiredSoftwareList";
import AboutToExpireSoftwareList from "./pages/abouttoexpiresoftware/AboutToExpireSoftwareList";
import AllDevicesList from "./pages/devicepages/devicelist/DeviceList";
import AboutToExpireDeviceList from "./pages/devicepages/abouttoexpiredevice/AboutToExpireDeviceList";
import ExpiredDeviceList from "./pages/devicepages/expireddevice/ExpiredDeviceList";
import DeviceCompaniesList from "./pages/devicepages/devicecompanies/DeviceCompanies";
import SoftwarePurchaseHistoryList from "./pages/softwarepages/softwarepurchasehistory/SoftwarePurchaseHistoryPage";
import AddNewDevice from "./pages/devicepages/adddevice/AddDevicePage";
import AddNewSoftware from "./pages/addsoftware/AddSoftwarePage";
import { SoftwareList } from "./pages/softwarepages/Softwarelist/SoftwareList";
import SoftwareComapniesList from "./pages/softwarepages/softwarecompanies/SoftwareCompanies";
import SoftwareRenewalForm from "./components/add/RenewSoftware";
import AllNotificationList from "./pages/notificationpage/NotificationPage";

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/">
            <Route index element={<Home />} />
            <Route path="login" element={<Login />} />
            <Route path="software/">
              <Route index element={<SoftwareList />} />
              <Route path="expiredSoftware" element={<ExpiredSoftwareList/>}/>
              <Route path="abouttoexpireSoftware" element={<AboutToExpireSoftwareList />} />
              <Route path="softwarecompanies" element={<SoftwareComapniesList />} />
              <Route path="addSoftware" element={<AddNewSoftware />} />
              <Route path="softwarepurchasehistory" element={<SoftwarePurchaseHistoryList />}/>
              <Route path="renew" element={<SoftwareRenewalForm />}/>

            </Route>
            <Route path="device/">
              <Route index element={<AllDevicesList/>} />
              <Route path="expireddevice" element={<ExpiredDeviceList />}/>
              <Route path="abouttoexpireDevice" element={<AboutToExpireDeviceList />} />
              <Route path="devicecompanies" element={< DeviceCompaniesList/>} />
              <Route path="adddevice" element={<AddNewDevice/>} />
            </Route>
            <Route path="notification">
              <Route index element={<AllNotificationList />} />
            </Route>
          </Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
