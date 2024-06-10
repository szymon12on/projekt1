import Rating from "./Rating";
function LatestRating() {
  return (
    <div className="w-[100%] flex flex-col mt-10 gap-y-5">
      <h3 className="text-2xl">Ostatnie Oceny</h3>
      <div>
        <Rating />
        <Rating />
        <Rating />
      </div>
    </div>
  );
}

export default LatestRating;
