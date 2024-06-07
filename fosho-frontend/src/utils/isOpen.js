function isOpen(isActive, openTime, closeTime) {
  if (!isActive) return false;
  const now = new Date();
  const hour = now.getHours();
  const minute = now.getMinutes();

  const nowFloat = parseFloat(`${hour}.${minute}`);
  const openTimeFloat = parseFloat(openTime.replace(":", "."));
  const closeTimeFloat = parseFloat(closeTime.replace(":", "."));

  console.log({ nowFloat, openTimeFloat, closeTimeFloat });

  return nowFloat > openTimeFloat && nowFloat < closeTimeFloat;
}

export { isOpen };
