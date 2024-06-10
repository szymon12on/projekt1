import MaterialItem from "../Components/Materials/MaterialItem";
import MaterialsContainer from "../Components/Materials/MaterialsContainer";
import MaterialsPage from "../Components/Materials/MaterialsPage";
import TitleMaterials from "../Components/Materials/TitleMaterials";
import UserInfo from "../Components/HomePage/UserInfo";
import InfoTab from "../Components/HomePage/InfoTab";
import useApi from "../Components/Materials/Hooks/useApi";
import Error from "../Components/Helpers/Error";
import Loader from "../Components/Helpers/Loader";
import { Outlet } from "react-router-dom";

import useHideContainer from "../Components/Materials/Hooks/useHideContainer";
import useNameMaterials from "../Components/Materials/Hooks/useNameMaterials";
import { useContext } from "react";
import UserContext from "../Components/Context/UserContext";

function Materials() {
  const { userInfo } = useContext(UserContext);

  const { data, isLoading, error } = useApi(
    `/api/przedmiot/all?idUcznia=${userInfo.id}`
  );
  const load = useHideContainer(false);
  const [nameMaterials, setNameMaterials] = useNameMaterials("");

  return (
    <MaterialsPage>
      <UserInfo />
      <InfoTab title={`${nameMaterials}`} />
      <TitleMaterials />

      <Outlet context={[nameMaterials, data, isLoading]} />
      {load && (
        <MaterialsContainer>
          {isLoading && <Loader />}
          {error ? (
            <Error />
          ) : (
            data?._embedded.przedmiotModelList.map((obj) => (
              <MaterialItem
                name={obj.nazwa}
                key={obj.id}
                setNameMaterials={setNameMaterials}
              />
            ))
          )}
        </MaterialsContainer>
      )}
    </MaterialsPage>
  );
}

export default Materials;
