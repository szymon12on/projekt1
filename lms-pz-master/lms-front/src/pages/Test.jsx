import TestContainer from "../Components/Test/TestContainer";
import UserInfo from "../Components/HomePage/UserInfo";
import InfoTab from "../Components/HomePage/InfoTab";
import TestContainerInfo from "../Components/Test/TestContainerInfo";
import TitleContainer from "../Components/Test/TitleContainer";
import TestItem from "../Components/Test/TestItem";
import useApi from "../Components/Materials/Hooks/useApi";
function Test() {
  const tekst = btoa("ZADANIE");
  const { data, isLoading, error } = useApi(
    `/api/przedmiot/zadanie/aktywne?typ=${tekst}`
  );

  return (
    <TestContainer>
      <UserInfo />
      <InfoTab title="Testy" />
      <TitleContainer />
      <TestContainerInfo>
        <TestItem />
        <TestItem />
        <TestItem />
        <TestItem />
        <TestItem />
        <TestItem />
        <TestItem />
        <TestItem />
      </TestContainerInfo>
    </TestContainer>
  );
}

export default Test;
