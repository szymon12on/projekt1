import ProfilePage from "../Components/Profile/ProfilePage";
import UserInfo from "../Components/HomePage/UserInfo";
import InfoTab from "../Components/HomePage/InfoTab";
import ProfileContainer from "../Components/Profile/ProfileContainer";
import LeftPanel from "../Components/Profile/LeftPanel";
import RightPanel from "../Components/Profile/RightPanel";
function Profile() {
  return (
    <ProfilePage>
      <UserInfo />
      <InfoTab title="Profil" />
      <ProfileContainer>
        <LeftPanel />
        <RightPanel />
      </ProfileContainer>
    </ProfilePage>
  );
}

export default Profile;
