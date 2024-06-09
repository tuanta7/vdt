function formatPrice(price, delimiter = ".") {
  if (typeof price !== "number") {
    throw new TypeError("The price should be a number");
  }
  let parts = price.toFixed(0).split(".");
  parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, delimiter);

  return parts.join(".");
}

function formatISODate(isoDate) {
  const date = new Date(isoDate);
  const formatter = new Intl.DateTimeFormat("vi-VN", {
    year: "numeric",
    month: "long",
    day: "numeric",
    weekday: "long",
    hour: "numeric",
    minute: "numeric",
    second: "numeric",
  });
  return formatter.format(date);
}

export { formatPrice, formatISODate };
