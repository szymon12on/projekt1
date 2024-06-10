import HomePage from "../Components/HomePage/HomePage";
import UserInfo from "../Components/HomePage/UserInfo";
import InfoTab from "../Components/HomePage/InfoTab";

import MainContent from "../Components/HomePage/MainContent";
function Home() {
  return (
    <HomePage>
      <UserInfo />
      <InfoTab title="Panel Glówny" />
      <MainContent />
    </HomePage>
  );
}

export default Home;
