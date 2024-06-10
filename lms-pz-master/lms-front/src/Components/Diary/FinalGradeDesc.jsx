function FinalGradeDesc({ children }) {
  return (
    <div className="w-[33%] ">
      <h3 className="text-white bg-darkBlueDiary px-2 py-2 border-l-[1px] border-white">
        Ocena końcowa
      </h3>
      {children}
    </div>
  );
}

export default FinalGradeDesc;
