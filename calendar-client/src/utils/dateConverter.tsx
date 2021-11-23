export const stringToDate = (date: string): Date | undefined => {
	const dateArr = date.split("-");
	if (dateArr.length != 3) return undefined;
	const d = new Date(date);
	console.log(d);
	return d;
};
/*
export const dateToString = (date: Date) => {

};

*/
