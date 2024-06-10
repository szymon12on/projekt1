import { useContext } from "react";
import UserContext from "../Context/UserContext";

function RightPanelInfoUser() {
  const { userData } = useContext(UserContext);

  return (
    <div className="mt-10 ml-10">
      <div className="flex items-center gap-x-5">
        <div className="w-[130px]">
          <img
            src={`data:image/jpeg;base64,${userData.zdjecie}`}
            alt="person"
            className="rounded-full"
          />
        </div>
        <div>
          <h2 className="text-3xl">
            {userData.imie} {userData.nazwisko}
          </h2>
          <p className="text-[13px] opacity-55">{userData.email}</p>
        </div>
      </div>
      <h3 className="mt-10 text-2xl">Dane Personalne</h3>
      <div className="grid grid-cols-2 mt-10 w-[100%]">
        <div className="w-[50%]">
          <p className="text-[12px]">Imie</p>
          <div className="border border-gray border-opacity-30 w-[300px] py-2 rounded-lg pl-3 opacity-60 mt-1 text-xl">
            {userData.imie}
          </div>
        </div>
        <div className="translate-x-[-100px]">
          <p className="text-[12px]">Nazwisko</p>
          <div className="border border-gray border-opacity-30 w-[300px] py-2 rounded-lg pl-3 opacity-60 mt-1 text-xl">
            {userData.nazwisko}
          </div>
        </div>
        <div className="mt-10">
          <p className="text-[12px]">Telefon</p>
          <div className="border border-gray border-opacity-30 w-[300px] py-2 rounded-lg pl-3 opacity-60 mt-1 text-xl">
            {userData.telefon}
          </div>
        </div>
        <div className="mt-10 translate-x-[-100px]">
          <p className="text-[12px]">Rola</p>
          <div className="border border-gray border-opacity-30 w-[300px] py-2 rounded-lg pl-3 opacity-60 mt-1 text-xl">
            {userData.rola}
          </div>
        </div>
      </div>
    </div>
  );
}

export default RightPanelInfoUser;
