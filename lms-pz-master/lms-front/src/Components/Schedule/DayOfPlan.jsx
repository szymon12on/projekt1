import InfoDay from "./InfoDay";
function DayOfPlan({ name, subjects }) {
  return (
    <div className="w-[100%]">
      <InfoDay name={name} subjects={subjects} />
    </div>
  );
}

export default DayOfPlan;
