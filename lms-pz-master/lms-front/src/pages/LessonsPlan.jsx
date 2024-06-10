import SchedulePage from "../Components/Schedule/SchedulePage";
import UserInfo from "../Components/HomePage/UserInfo";
import InfoTab from "../Components/HomePage/InfoTab";
import ScheduleContainer from "../Components/Schedule/ScheduleContainer";
import DayOfPlan from "../Components/Schedule/DayOfPlan";
const timeData = [
  {
    name: "Poniedzialek",
    subject: ["Geografia", "Fizyka", "Matematyka", "Angielski", "Polski", "WF"],
  },
  {
    name: "Wtorek",
    subject: ["Polski", "Chemia", "Angielski", "Rosyjski", "Biologia", "WF"],
  },
  {
    name: "Sroda",
    subject: [
      "Matematyka",
      "Historia",
      "Polski",
      "Biologia",
      "Biologia",
      "Matematyka",
    ],
  },
  {
    name: "Czwartek",
    subject: [
      "Matematyka",
      "Historia",
      "Polski",
      "Biologia",
      "Biologia",
      "Niemiecki",
    ],
  },
  {
    name: "Piatek",
    subject: [
      "Matematyka",
      "Matematyka",
      "Polski",
      "Biologia",
      "Biologia",
      "Niemiecki",
    ],
  },
];
function LessonsPlan() {
  return (
    <SchedulePage>
      <UserInfo />
      <InfoTab title="Plan Zajęć" />
      <ScheduleContainer>
        {timeData.map((item, index) => (
          <DayOfPlan key={index} name={item.name} subjects={item.subject} />
        ))}
      </ScheduleContainer>
    </SchedulePage>
  );
}

export default LessonsPlan;
