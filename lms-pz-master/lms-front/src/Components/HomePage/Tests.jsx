import Test from "./Test";
function Tests() {
  return (
    <div className="w-[100%] flex flex-col items-end mt-5 gap-y-5">
      <h2 className="text-left w-[50%] border-b-[1px] border-blue text-2xl text-blue">
        Testy
      </h2>
      <Test />
      <Test />
      <Test />
    </div>
  );
}

export default Tests;
