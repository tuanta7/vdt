function formatPrice(price, delimiter = ".") {
  if (typeof price !== "number") {
    throw new TypeError("The price should be a number");
  }
  let parts = price.toFixed(0).split(".");
  parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, delimiter);

  return parts.join(".");
}

export { formatPrice };
