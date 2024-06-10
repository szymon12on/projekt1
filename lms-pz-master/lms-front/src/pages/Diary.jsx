import DiaryPage from "../Components/Diary/DiaryPage";
import UserInfo from "../Components/HomePage/UserInfo";
import InfoTab from "../Components/HomePage/InfoTab";
import DiaryContainer from "../Components/Diary/DiaryContainer";
import SubjectDiary from "../Components/Diary/SubjectDiary";
import SingleSubject from "../Components/Diary/singleSubject";
import FirstPeriod from "../Components/Diary/FirstPeriod";
import RatingPeriod from "../Components/Diary/RatingPeriod";
import FinalGradeDesc from "../Components/Diary/FinalGradeDesc";
import FinalGrade from "../Components/Diary/FinalGrade";
import { useContext, useState } from "react";
import UserContext from "../Components/Context/UserContext";
import useApi from "../Components/Materials/Hooks/useApi";
import Loader from "../Components/Helpers/Loader";
import OptionContainer from "../Components/Diary/OptionContainer";

function Diary() {
  const { userData } = useContext(UserContext);
  const link = `/api/uczen/oceny/${userData.id}`;
  const { data, isLoading, error } = useApi(link);
  const [period, setPeriod] = useState("okres1");

  if (!data) {
    return <Loader />;
  }
  const dataArrayOfPeriods = data._embedded.zaliczenieModelList;
  const correctObj = dataArrayOfPeriods.find((obj) => obj.okres === period);
  const diaryData = correctObj.przedmioty;

  return (
    <DiaryPage>
      <UserInfo />
      <InfoTab title="Dziennik" />
      <OptionContainer data={dataArrayOfPeriods} setPeriod={setPeriod} />
      <DiaryContainer>
        <SubjectDiary>
          {diaryData.map((subject, index) => {
            if (index % 2 !== 0) {
              return (
                <SingleSubject
                  key={index}
                  bgColor="lightSlate"
                  text={subject.nazwa}
                />
              );
            } else {
              return <SingleSubject key={index} text={subject.nazwa} />;
            }
          })}
        </SubjectDiary>
        <FirstPeriod title={"Oceny cząstkowe 1 okres"}>
          {diaryData.map((subject, index) => {
            if (index % 2 !== 0) {
              return (
                <RatingPeriod
                  key={index}
                  bgColor="lightSlate"
                  partialGrades={subject.ocenyCzastkowe}
                />
              );
            } else {
              return (
                <RatingPeriod
                  key={index}
                  partialGrades={subject.ocenyCzastkowe}
                />
              );
            }
          })}
        </FirstPeriod>
        <FinalGradeDesc>
          {diaryData.map((subject, index) => {
            if (index % 2 !== 0) {
              return (
                <FinalGrade
                  key={index}
                  bgColor="lightSlate"
                  finalGrade={subject.ocenaKocnowa}
                />
              );
            } else {
              return (
                <FinalGrade key={index} finalGrade={subject.ocenaKocnowa} />
              );
            }
          })}
        </FinalGradeDesc>
      </DiaryContainer>
    </DiaryPage>
  );
}

export default Diary;
