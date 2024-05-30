/** @type {import('tailwindcss').Config} */

import daisyui from "daisyui";

export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      backgroundImage: {
        auth: "url('/auth-bg.png')",
      },
    },
  },
  plugins: [daisyui],
  daisyui: {
    themes: [
      {
        custom: {
          primary: "#FE4144",
          secondary: "#FBBF24",
          error: "#FE2053",
          info: "#2563EB",
          "base-100": "#F8FDEF",
          "base-200": "#E1E6D9",
          "base-300": "#CBCFC3",
        },
      },
      "lemonade",
    ],
  },
};
