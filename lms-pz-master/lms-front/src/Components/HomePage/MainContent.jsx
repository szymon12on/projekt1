import Chart from "./Chart";
import TimeTable from "./TimeTable";
import Tests from "./Tests";
import LatestRating from "./LatestRating";
import Calendar from "./Calendar";
function MainContent() {
  return (
    <div className="flex gap-x-20">
      <div className="w-[50%]">
        <Chart />
        {/* <Calendar /> */}
        <TimeTable />
      </div>
      <div>
        <Tests />
        <LatestRating />
      </div>
    </div>
  );
}

export default MainContent;
