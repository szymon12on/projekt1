import MainRightPanel from "./MainRightPanel";
import SettingsRightPanel from "./SettingsRightPanel";
function RightPanel() {
  return (
    <div className="w-[80%]">
      <MainRightPanel />
      <SettingsRightPanel />
    </div>
  );
}

export default RightPanel;
