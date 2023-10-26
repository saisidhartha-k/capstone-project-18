import "./App.css";

import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./pages/home/Home";
import Login from "./pages/login/Login";
import ExpiredSoftwareList from "./pages/softwarepages/ExpiredSoftwareList";
import AboutToExpireSoftwareList from "./pages/softwarepages/AboutToExpireSoftwareList";
import AllDevicesList from "./pages/devicepages/DeviceList";
import AboutToExpireDeviceList from "./pages/devicepages/AboutToExpireDeviceList";
import ExpiredDeviceList from "./pages/devicepages/ExpiredDeviceList";
import DeviceCompaniesList from "./pages/devicepages/DeviceCompanies";
import AddNewDevice from "./pages/devicepages/AddDevicePage";
import AddNewSoftware from "./pages/softwarepages/AddSoftwarePage";
import AllNotificationList from "./pages/NotificationPage";
import RMAList from "./pages/RMAPage";
import DevicePurchaseHistoryList from "./pages/devicepages/DevicePurchaseHistoryPage";
import DecomissionedItemsList from "./pages/DecomissionedItemsPage";
import SoftwareComapniesList from "./pages/softwarepages/SoftwareCompanies";
import SoftwareList from "./pages/softwarepages/SoftwareList";
import SoftwarePurchaseHistoryList from "./pages/softwarepages/SoftwarePurchaseHistoryPage";

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/">
            <Route index element={<Login />} />
            <Route path="home" element={<Home />} />
            <Route id="softwares" path="software/">
              <Route index element={<SoftwareList />} />
              <Route path="expiredSoftware" element={<ExpiredSoftwareList/>}/>
              <Route path="abouttoexpireSoftware" element={<AboutToExpireSoftwareList />} />
              <Route path="softwarecompanies" element={<SoftwareComapniesList />} />
              <Route path="addSoftware" element={<AddNewSoftware />} />
              <Route path="softwarepurchasehistory" element={<SoftwarePurchaseHistoryList />}/>
            </Route>
            <Route path="device/">
              <Route index element={<AllDevicesList/>} />
              <Route path="expireddevice" element={<ExpiredDeviceList />}/>
              <Route path="abouttoexpireDevice" element={<AboutToExpireDeviceList />} />
              <Route path="devicecompanies" element={< DeviceCompaniesList/>} />
              <Route path="adddevice" element={<AddNewDevice/>} />
              <Route path="devicepurchases" element={<DevicePurchaseHistoryList />} />
            </Route>
            <Route path="notification">
              <Route index element={<AllNotificationList />} />
            </Route>
            <Route path="rma">
              <Route index element={<RMAList />} />
            </Route>
            <Route path="decomissioneditems">
              <Route index element={<DecomissionedItemsList />} />
            </Route>
          </Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
