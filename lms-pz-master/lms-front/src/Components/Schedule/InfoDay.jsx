import SubjectInfo from "./SubjectInfo";
import TitleInfoDay from "./TitleInfoDay";
function InfoDay({ name, subjects }) {
  return (
    <div className="bg-lightSlate mb-5 mt-5 rounded-lg pb-10">
      <TitleInfoDay name={name} />
      <div className="bg-white w-[90%] mx-auto rounded-lg overflow-y-scroll scrollbar-thin scrollbar-thumb-green scrollbar-track-white max-h-[400px]">
        {subjects.map((item, index) => (
          <SubjectInfo key={index} name={item} index={index + 1} />
        ))}
      </div>
    </div>
  );
}

export default InfoDay;
