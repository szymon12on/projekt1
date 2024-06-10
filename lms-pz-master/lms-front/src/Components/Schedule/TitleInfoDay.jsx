function TitleInfoDay({ name }) {
  return (
    <div className="flex justify-between py-3 px-2 items-center">
      <h3 className="font-medium text-lg">{name}</h3>
      <p className="text-gray opacity-50">31 Listopada</p>
    </div>
  );
}

export default TitleInfoDay;
