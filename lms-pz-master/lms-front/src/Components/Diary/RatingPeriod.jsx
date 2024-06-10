function RatingPeriod({ bgColor, partialGrades }) {
  return (
    <div
      className={`px-2 py-5 text-lg bg-${bgColor} border-l-[1px] border-white h-[80px]`}
    >
      <p className="tex-[15px]">
        {partialGrades ? partialGrades.join(" , ") : "Brak ocen"}
      </p>
    </div>
  );
}

export default RatingPeriod;
