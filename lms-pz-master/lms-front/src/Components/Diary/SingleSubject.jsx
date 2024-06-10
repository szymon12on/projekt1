function SingleSubject({ bgColor, text }) {
  return (
    <div className={`px-2 bg-${bgColor} py-5 text-lg h-[80px]`}>
      <h3 className="text-[16px]">{text}</h3>
    </div>
  );
}

export default SingleSubject;
