function ErrorDisplay({ message }) {
  return (
    <p className="text-red mt-5 ml-5">
      <span>💥</span> {message}
    </p>
  );
}
export default ErrorDisplay;
