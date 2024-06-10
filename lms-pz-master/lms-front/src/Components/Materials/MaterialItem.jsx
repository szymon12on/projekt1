import { useRef } from "react";
import { NavLink } from "react-router-dom";
const colors = [
  "#4378FF",
  "#37C1A8",
  "#c08FFF",
  "#FE9636",
  "#F26C6C",
  "#553452",
];
function MaterialItem({ name, setNameMaterials }) {
  const randomNum = Math.floor(Math.random() * colors.length);
  const textRef = useRef(null);

  function handleTextFromLink() {
    const text = textRef.current.textContent;
    setNameMaterials(text);
  }

  return (
    <div className=" w-[350px] shadow-lg  rounded-lg border-opacity-50">
      <div
        style={{ backgroundColor: `${colors[randomNum]}` }}
        className={` w-[350px] h-[130px] rounded-lg cursor-pointer`}
      ></div>
      <NavLink to="Przedmiot" onClick={handleTextFromLink}>
        <h5 className="py-5 px-4 cursor-pointer text-lg" ref={textRef}>
          {name}
        </h5>
      </NavLink>
    </div>
  );
}

export default MaterialItem;
