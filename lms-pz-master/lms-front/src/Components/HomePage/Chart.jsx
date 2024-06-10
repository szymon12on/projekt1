import {
  LineChart,
  Line,
  CartesianGrid,
  XAxis,
  Tooltip,
  Legend,
  YAxis,
} from "recharts";

function Chart() {
  const data = [
    {
      name: "Pon",
      min: 50,
      amt: 12,
    },
    {
      name: "Wt",
      min: 30,
      amt: 40,
    },
    {
      name: "Śr",
      min: 30,
      amt: 20,
    },
    {
      name: "Czw",
      min: 11,
      amt: 67,
    },
    {
      name: "Pt",
      min: 88,
      amt: 44,
    },

    {
      name: "Sob",
      min: 55,
      amt: 32,
    },
    {
      name: "Nd",
      min: 20,
      amt: 18,
    },
  ];

  return (
    <div className="mt-10">
      <LineChart
        width={730}
        height={250}
        data={data}
        margin={{ top: 5, right: 30, left: 20, bottom: 5 }}
      >
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="name" />
        <YAxis />
        <Tooltip />
        <Legend />
        <Line type="monotone" dataKey="min" stroke="#8884d8" />
      </LineChart>
    </div>
  );
}

export default Chart;
