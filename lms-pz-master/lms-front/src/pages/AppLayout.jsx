import NavBar from "../Components/AppLayout/NavBar";
import Logo from "../Components/AppLayout/Logo";
import Layout from "../Components/AppLayout/Layout";
import Header from "../Components/AppLayout/header";
import Main from "../Components/AppLayout/Main";
import { Outlet } from "react-router-dom";
function AppLayout() {
  return (
    <Layout>
      <Header>
        <Logo />
        <NavBar />
      </Header>
      <Main>
        <Outlet />
      </Main>
    </Layout>
  );
}

export default AppLayout;
