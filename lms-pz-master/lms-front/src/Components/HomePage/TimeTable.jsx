import Subject from "./Subject";
import TitleSubject from "./TitleSubject";
import SubjectContainer from "./SubjectContainer";
import InfoTimeTable from "./InfoTimeTable";
import InfoTimeSubjectContainer from "./InfoTimeSubjectContainer";
import { useState } from "react";

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

function TimeTable() {
  const [dayWeek, setDayWeek] = useState("Poniedzialek");
  let items = [];
  return (
    <div className="ml-5 mt-10 w-[100%]">
      <TitleSubject setDayWeek={setDayWeek} dayWeek={dayWeek} />
      <InfoTimeSubjectContainer>
        <InfoTimeTable dayWeek={dayWeek} timeData={timeData} />
        <SubjectContainer>
          {timeData.forEach((data) => {
            if (data.name === dayWeek) {
              data.subject.forEach((item) => items.push(item));
            }
          })}

          {items.map((item, index) => (
            <Subject key={index} name={item} index={index + 1} />
          ))}
        </SubjectContainer>
      </InfoTimeSubjectContainer>
    </div>
  );
}

export default TimeTable;
