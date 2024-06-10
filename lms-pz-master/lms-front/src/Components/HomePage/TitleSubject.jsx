function TitleSubject({ setDayWeek, dayWeek }) {
  function handleDayWeek(day) {
    setDayWeek(day);
  }

  return (
    <div className="flex justify-between ]">
      <h4 className="text-2xl">Plan Zajęć</h4>
      <div className="flex gap-x-5 cursor-pointer">
        <p
          className={`base-timeTable ${
            dayWeek === "Poniedzialek" ? "active-timeTable" : ""
          }`}
          onClick={() => handleDayWeek("Poniedzialek")}
        >
          Pon
        </p>
        <p
          className={`base-timeTable ${
            dayWeek === "Wtorek" ? "active-timeTable" : ""
          }`}
          onClick={() => handleDayWeek("Wtorek")}
        >
          Wt
        </p>
        <p
          className={`base-timeTable ${
            dayWeek === "Sroda" ? "active-timeTable" : ""
          }`}
          onClick={() => handleDayWeek("Sroda")}
        >
          Sr
        </p>
      </div>
    </div>
  );
}

export default TitleSubject;
