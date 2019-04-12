export interface IContact {
    id?: number;
    firstName?: string;
    lastName?: string;
    email?: string;
    mobile?: string;
    createdBy?: string;
}

export class Contact implements IContact {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public mobile?: string,
        public createdBy?: string
    ) {}
}
