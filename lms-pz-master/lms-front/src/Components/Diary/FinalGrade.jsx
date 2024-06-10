function FinalGrade({ bgColor, finalGrade }) {
  return (
    <p
      className={`w-[100%] text-center px-2 py-5 text-lg bg-${bgColor} border-l-[1px] border-white h-[80px] text-[15px]`}
    >
      {finalGrade}
    </p>
  );
}

export default FinalGrade;
