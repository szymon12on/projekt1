function FirstPeriod({ children, title }) {
  return (
    <div className="w-[33%] ">
      <h2 className="bg-darkBlueDiary text-white px-2 py-2 border-l-[1px] border-white">
        {title}
      </h2>
      {children}
    </div>
  );
}

export default FirstPeriod;
