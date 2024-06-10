import { useContext, useEffect, useState } from "react";
import NotificationContext from "./NotificationContext";
import UserContext from "./UserContext";
function NotificationContextProvider({ children }) {
  const { userInfo } = useContext(UserContext);
  const [notificationData, setNotificationData] = useState(null);
  useEffect(
    function () {
      if (!userInfo) return;
      const login = btoa(userInfo.login);
      const token = JSON.parse(localStorage.getItem("token"));

      const headers = {
        Authorization: `Bearer ${token}`,
      };

      const fetchData = {
        method: "GET",
        headers: headers,
      };

      const getData = async function () {
        const res = await fetch(
          `/api/powiadomienie/all?login=${login}`,
          fetchData
        );
        if (!res.ok) throw new Error("Blad");
        const answer = await res.json();
        const correctData = answer._embedded.powiadomienieModelList;
        setNotificationData(correctData);
      };
      getData();
    },
    [userInfo]
  );
  return (
    <NotificationContext.Provider value={{ notificationData }}>
      {children}
    </NotificationContext.Provider>
  );
}

export default NotificationContextProvider;
