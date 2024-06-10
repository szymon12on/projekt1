import { useEffect, useState } from "react";
import UserContext from "./UserContext";

function UserContextProvider({ children }) {
  const [userInfo, setUserInfo] = useState(null);
  const [userData, setUserData] = useState(null);

  useEffect(
    function () {
      if (!userInfo) return;
      const getData = async () => {
        const headers = {
          Authorization: `Bearer ${userInfo.token}`,
        };

        const fetchData = {
          method: "GET",
          headers: headers,
        };
        const res = await fetch(`/api/uzytkownik/${userInfo.id}`, fetchData);

        if (!res.ok) throw new Error("Something went wrong");
        const data = await res.json();
        setUserData(data);
      };
      getData();
    },
    [userInfo]
  );

  return (
    <UserContext.Provider value={{ userInfo, setUserInfo, userData }}>
      {children}
    </UserContext.Provider>
  );
}

export default UserContextProvider;
