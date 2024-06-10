function SubjectInfo({ name, index }) {
  return (
    <div className="flex ml-3 space-x-5 border-b-[1px] border-lightSlate py-3 items-center ">
      <p className="border-r-[2px] border-green pr-2">{index}</p>
      <div>
        <h5>{name}</h5>
        <p className="text-gray opacity-50">8.00-8.45</p>
      </div>
    </div>
  );
}

export default SubjectInfo;
