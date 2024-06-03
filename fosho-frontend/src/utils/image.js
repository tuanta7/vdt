function crop(url, width, height) {
  if (!url) return "/no-img.png";
  const parts = url.split("/upload/");
  return `${parts[0]}/upload/c_crop,h_${height},w_${width}/${parts[1]}`;
}

function fill(url, width, height) {
  if (!url) return "/no-img.png";
  const parts = url.split("/upload/");
  return `${parts[0]}/upload/c_fill,w_${width},h_${height}/${parts[1]}`;
}

export { crop, fill };
