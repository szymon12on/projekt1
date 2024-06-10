import { useContext } from "react";
import UserContext from "../Context/UserContext";
import LogOutComponent from "./LogOutComponent";
import Loader from "../Helpers/Loader";
import NotificationContext from "../Context/NotificationContext";
import NotificationComponent from "./NotificationComponent";
function UserInfo() {
  const { userData } = useContext(UserContext);
  const { notificationData } = useContext(NotificationContext);

  if (!userData || !notificationData) {
    return <Loader />;
  }

  return (
    <div className="flex items-center justify-between">
      <div className="flex items-center gap-x-5">
        <div className="w-[60px] h-auto ml-5 mt-5 rounded-lg ">
          <img
            src={`data:image/jpeg;base64,${userData.zdjecie}`}
            alt="person"
          />
        </div>
        <h3 className="translate-y-[13px] ">
          {userData.imie} {userData.nazwisko}
        </h3>
      </div>

      <LogOutComponent />
      <NotificationComponent notificationData={notificationData} />
    </div>
  );
}

export default UserInfo;
