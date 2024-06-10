import handleLogOut from "../Helpers/handleLogOut";
function LeftPanelLogout() {
  return (
    <div className="flex items-center pt-5 gap-x-2 border-b border-gray pl-5 pb-3 border-opacity-15 cursor-pointer">
      <span onClick={handleLogOut}>
        <svg
          width="30px"
          height="30px"
          viewBox="0 0 24 24"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            d="M15 4H18C19.1046 4 20 4.89543 20 6V18C20 19.1046 19.1046 20 18 20H15M8 8L4 12M4 12L8 16M4 12L16 12"
            stroke="#F26C6C"
            strokeWidth="1.5"
            strokeLinecap="round"
            strokeLinejoin="round"
          />
        </svg>
      </span>
      <p className="opacity-55" onClick={handleLogOut}>
        Wyloguj
      </p>
    </div>
  );
}

export default LeftPanelLogout;
