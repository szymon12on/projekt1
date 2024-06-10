export default function Calendar() {
  let month_shift = 0; // For debugging
  let now = new Date();
  let first_day_index = new Date(
    now.getFullYear(),
    now.getMonth() + month_shift,
    0
  ).getDay();
  let curr_day = now.getDate();
  let days = new Date(
    now.getFullYear(),
    now.getMonth() + 1 + month_shift,
    0
  ).getDate();
  let weeks = Math.ceil(days / 7);

  let classes_0 =
    "bg-slate h-10 w-10 p-2 mx-2 inline-block rounded shadow-md cursor-pointer text-gray-600 hover:bg-darkBlue hover:text-gray-100 transition duration-700 ease-in-out";
  let classes_1 =
    "bg-slate h-10 w-10 p-2 mx-2 inline-block rounded shadow-md cursor-pointer text-gray-600 hover:bg-darkBlue hover:text-gray-100 transition duration-700 ease-in-out";
  let classes_2 =
    "bg-slate h-10 w-10 p-2 mx-2 inline-block rounded shadow-md cursor-pointer text-gray-600 hover:bg-darkBlue hover:text-gray-100 transition duration-700 ease-in-out";
  let days_array = [];
  // Padding missing days (from previous month)
  let d = new Date();
  d.setDate(1);
  d.setHours(-1);
  let prev_date = d.getDate() - first_day_index + 1;
  for (let j = 0; j < first_day_index; ++j) {
    days_array.push(<span className={classes_0}>{prev_date++}</span>);
  }
  // Pushing the days of this month to the array
  for (var i = 0; i < weeks; ++i) {
    for (var j = i * 7; j < (i + 1) * 7 && j < days; ++j) {
      var day = j + 1;
      if (day === curr_day) {
        days_array.push(<span className={classes_2}>{day}</span>);
      } else {
        days_array.push(<span className={classes_1}>{day}</span>);
      }
    }
  }
  weeks = Math.ceil(days_array.length / 7);
  // Padding missing days (from next month)
  var missing_days = weeks * 7 - days_array.length;
  for (let j = 0; j < missing_days; ++j) {
    let day = j + 1;
    days_array.push(<span className={classes_0}>{day}</span>);
  }
  // Creating the weeks array
  var weeks_array = [<div style={{ height: 1 + "px" }}></div>];
  for (let i = 0; i < Math.ceil(days_array.length / 7); ++i) {
    let this_week_array = [];
    for (let j = i * 7; j < (i + 1) * 7 && j < days_array.length; ++j) {
      this_week_array.push(days_array[j]);
    }
    weeks_array.push(<div className="mt-4">{this_week_array}</div>);
  }
  // Return the calendar
  return (
    <div
      className="bg-violetLight rounded ml-5 mt-5"
      style={{ width: 395 + "px", height: 59 * weeks + "px" }}
    >
      {weeks_array}
    </div>
  );
}
