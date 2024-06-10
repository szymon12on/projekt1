import LeftPanelInfoUser from "./LeftPanelInfoUser";
import LeftPanelInfoSettings from "./LeftPanelInfoSettings";
import LeftPanelLogout from "./LeftPanelLogout";
function LeftPanel() {
  return (
    <div className=" bg-lightBgSlate w-[20%] border-r border-slate">
      <LeftPanelInfoUser />
      <LeftPanelInfoSettings />
      <LeftPanelLogout />
    </div>
  );
}

export default LeftPanel;
