export class Employee {

    public constructor(init?: Partial<Employee>) {
        Object.assign(this, init);
    }

    id: String;
    login:String;
    name: String;
    salary: number;
    version:number;
}