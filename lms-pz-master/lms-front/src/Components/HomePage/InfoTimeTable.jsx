function InfoTimeTable({ dayWeek, timeData }) {
  function giveActualDay(timeData) {
    let day;
    timeData.forEach((data) => {
      if (data.name === dayWeek) {
        day = data.name;
      }
    });
    return day;
  }

  function giveNumberLessons(day, timeData) {
    let lessons;
    timeData.forEach((data) => {
      if (data.name == day) {
        lessons = data.subject.length;
      }
    });
    return lessons;
  }

  const actualDay = giveActualDay(timeData);
  const lessons = giveNumberLessons(actualDay, timeData);
  return (
    <div className="flex justify-between items-center mx-5">
      <h5 className="text-medium text-xl">{dayWeek}</h5>
      <p className="opacity-50">{lessons} Lekcji</p>
    </div>
  );
}

export default InfoTimeTable;
