/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    colors: {
      transparent: "transparent",
      blue: "#4378FF",
      green: "#37C1A8",
      violet: "#c08FFF",
      orange: "#FE9636",
      red: "#F26C6C",
      white: "#fff",
      darkBlue: "#265073",
      slate: "#e2e8f0",
      gray: "#71717a",
      lightSlate: "#f1f5f9",
      violetLight: "#8dc6ff",
      darkGray: "#22313f",
      darkBlueDiary: "#190482",
      customColor: "#553452",
      lightBgSlate: "#f8fafc",
      black: "#000",
    },
    fontFamily: {
      poppins: ["Poppins", "sans-serif"],
    },
    extend: {},
  },
  plugins: [require("tailwind-scrollbar")],
};
